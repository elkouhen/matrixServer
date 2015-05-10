package com.softeam.formations.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.softeam.formations.datalayer.dto.Matrix;
import com.softeam.formations.datalayer.dto.Pair;
import com.softeam.formations.resource.impl.MatrixResourceV1Impl;

@Path(MatrixResourceV1Impl.RESOURCE + MatrixResourceV1Impl.VERSION)
@Consumes("application/json")
@Produces("application/json")
public interface MatrixResourceV1 {

	@POST
	@Path("/power")
	public void power(@Suspended AsyncResponse response, final Pair<Matrix, Integer> m) throws JsonProcessingException;
}
