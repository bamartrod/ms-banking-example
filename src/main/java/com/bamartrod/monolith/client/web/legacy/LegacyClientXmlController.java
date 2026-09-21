package com.bamartrod.monolith.client.web.legacy;

import com.bamartrod.monolith.client.ClientService;
import com.bamartrod.monolith.platform.kernel.Result;
import com.bamartrod.monolith.platform.web.CorrelationContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * REST controller for LegacyClientXml bounded context. Handles HTTP requests and delegates to application use cases.
 *
 * @author Brandon Martinez
 */


@RestController
@RequestMapping(path = "${service.controller.path:/ms-client-query}")

public class LegacyClientXmlController {

    private final ClientService clientService;
    private final IBRequestParser parser;

    public LegacyClientXmlController(ClientService clientService, IBRequestParser parser) {
        this.clientService = clientService;
        this.parser = parser;
    }

    @PostMapping(path = "/client/secure_data", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> secureData(@RequestBody String rawXml,
                                             @RequestHeader(value = "X-Correlation-Id", required = false) String header) {
        var cid = CorrelationContext.resolve(header);
        var payload = parser.extractSecureDataPayload(rawXml);
        var result = clientService.findSecureData(payload.nit(), cid);
        return switch (result) {
            case Result.Success(var data) -> ResponseEntity.ok().header("X-Correlation-Id", cid.value()).body(parser.toSecureDataResponse(data.maskedAccount()));
            case Result.Failure(var err) -> ResponseEntity.status(err.httpStatus()).header("X-Correlation-Id", cid.value()).body(parser.toNotFoundResponse(err.code()));
        };
    }

    @PostMapping(path = "/client/general", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> general(@RequestBody String rawXml,
                                          @RequestHeader(value = "X-Correlation-Id", required = false) String header) {
        var cid = CorrelationContext.resolve(header);
        var payload = parser.extractSecureDataPayload(rawXml);
        var result = clientService.findGeneralInfo(payload.nit(), cid);
        return switch (result) {
            case Result.Success(var data) -> ResponseEntity.ok().header("X-Correlation-Id", cid.value()).body(parser.toSecureDataResponse(data.documentNumber()));
            case Result.Failure(var err) -> ResponseEntity.status(err.httpStatus()).header("X-Correlation-Id", cid.value()).body(parser.toNotFoundResponse(err.code()));
        };
    }
}
