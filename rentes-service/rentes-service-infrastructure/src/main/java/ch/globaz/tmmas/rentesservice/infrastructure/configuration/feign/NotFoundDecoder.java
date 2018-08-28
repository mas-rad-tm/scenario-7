package ch.globaz.tmmas.rentesservice.infrastructure.configuration.feign;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Optional;

public class NotFoundDecoder implements Decoder{

	private final Decoder delegate;

	public NotFoundDecoder(Decoder delegate) {
		Assert.notNull(delegate, "Can't build this decoder with a null delegated decoder");

		this.delegate = delegate;
	}

	@Override
	public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
		if (!(type instanceof ParameterizedType)) {
			return delegate.decode(response, type);
		}

		if (isParameterizedTypeOf(type, Optional.class)) {
			return decodeOptional(response, type);
		}

		if (isParameterizedTypeOf(type, ResponseEntity.class)) {
			return decodeResponseEntity(response, type);
		}

		return delegate.decode(response, type);
	}

	private boolean isParameterizedTypeOf(Type type, Class<?> clazz) {
		ParameterizedType parameterizedType = (ParameterizedType) type;

		return parameterizedType.getRawType().equals(clazz);
	}

	private Object decodeOptional(Response response, Type type) throws IOException {
		if (response.status() == 404) {
			return Optional.empty();
		}

		Type enclosedType = Util.resolveLastTypeParameter(type, Optional.class);
		Object decodedValue = delegate.decode(response, enclosedType);

		if (decodedValue == null) {
			return Optional.empty();
		}

		return Optional.of(decodedValue);
	}

	private Object decodeResponseEntity(Response response, Type type) throws IOException {
		if (response.status() == 404) {
			return ResponseEntity.notFound().build();
		}

		return delegate.decode(response, type);
	}
}
