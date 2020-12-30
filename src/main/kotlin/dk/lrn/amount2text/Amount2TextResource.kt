package dk.lrn.amount2text

import java.lang.IllegalArgumentException
import java.math.BigDecimal
import javax.ws.rs.*
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider
import javax.ws.rs.core.MediaType

@Path("/")
class Amount2TextResource {
    @POST
    fun amount2Text(amount: Amount): AmountAsText {
        return AmountAsText(NumberToString.convert(amount.input))
    }
}

@Suppress("unused")
class Amount(var input: BigDecimal){
    constructor() : this(BigDecimal("0.0"))
}

@Suppress("unused")
class AmountAsText(var output: String){
    constructor() : this("")
}

@Provider
class MyApplicationExceptionHandler : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException): Response {
        return Response.status(Response.Status.BAD_REQUEST).entity("{ \"message\":\"${exception.message}\"}").type(MediaType.APPLICATION_JSON).build()
    }
}