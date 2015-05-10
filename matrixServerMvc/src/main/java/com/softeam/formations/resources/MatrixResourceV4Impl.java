package com.softeam.formations.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import com.softeam.formations.datalayer.dao.IMatrixRepository;
import com.softeam.formations.datalayer.dto.Matrix;
import com.softeam.formations.datalayer.dto.Pair;
import com.softeam.formations.resources.helpers.MatrixHelper;
import com.sun.corba.se.impl.util.Version;

@RestController
@RequestMapping(value = MatrixResourceV4Impl.RESOURCE + MatrixResourceV4Impl.VERSION, method = RequestMethod.POST)
public class MatrixResourceV4Impl {

	public static final String HOST = "http://localhost:8080";
	public static final String RESOURCE = "/matrix/";
	public static final String VERSION = "V4";
	public static final String POWER = "/power";

	@Autowired
	private IMatrixRepository repository;

	@Autowired
	private AsyncRestTemplate restTemplate;

	@Autowired
	private MatrixHelper matrixHelper;

	@RequestMapping(value = POWER, method = RequestMethod.POST)
	public DeferredResult<Matrix> power(@RequestBody final Pair<String, Integer> m) {

		final DeferredResult<Matrix> deferredResult = new DeferredResult<Matrix>();

		final Matrix result = repository.findById(m.getLeft());

		if (m.getRight() == 1) {

			deferredResult.setResult(result);
			return deferredResult;
		}

		final Pair<String, Integer> operation = new Pair<String, Integer>(m.getLeft(), m.getRight() - 1);

		restTemplate.exchange(HOST + RESOURCE + VERSION + POWER, HttpMethod.POST, new HttpEntity<Object>(operation), Matrix.class).addCallback(
				new ListenableFutureCallback<ResponseEntity<Matrix>>() {
					@Override
					public void onFailure(Throwable ex) {
						deferredResult.setErrorResult(ex.getMessage());
					}

					@Override
					public void onSuccess(ResponseEntity<Matrix> response) {
						deferredResult.setResult(matrixHelper.multiply(result, response.getBody()));
					}
				});

		return deferredResult;
	}
}
