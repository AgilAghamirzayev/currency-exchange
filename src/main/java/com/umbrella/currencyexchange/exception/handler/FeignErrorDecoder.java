package com.umbrella.currencyexchange.exception.handler;

import com.umbrella.currencyexchange.exception.BadRequestException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.naming.ServiceUnavailableException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400:
                yield new BadRequestException(response.reason());
            case 404: {
                if (methodKey.contains(".xml")) {
                    yield new ResponseStatusException(HttpStatusCode.valueOf(response.status()), "Currencies are not found");
                }
            }
            yield new ChangeSetPersister.NotFoundException();
            case 503:
                yield new ServiceUnavailableException(response.reason());
            default:
                yield new Exception(response.reason());
        };
    }
}
