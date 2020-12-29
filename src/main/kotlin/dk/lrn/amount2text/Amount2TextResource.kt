package dk.lrn.amount2text

import com.fasterxml.jackson.annotation.JsonCreator
import java.lang.IllegalArgumentException
import java.math.BigDecimal
import javax.ws.rs.*
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Path("/")
class Amount2TextResource {
    @POST
    fun amount2Text(amount: Amount): AmountAsText {
        return AmountAsText(NumberToString.convert(amount.input))
    }
}

class Amount @JsonCreator constructor(var input: BigDecimal)

data class AmountAsText @JsonCreator constructor(var output: String)

data class ExceptionEntity @JsonCreator constructor(var message: String?)

@Provider
class MyApplicationExceptionHandler : ExceptionMapper<IllegalArgumentException> {
    override fun toResponse(exception: IllegalArgumentException): Response {
        return Response.status(Response.Status.BAD_REQUEST).entity(ExceptionEntity(exception.message)).build()
    }
}