package edu.austral.dissis.common.results

import edu.austral.dissis.common.interfaces.Result

class InvalidResult(private val message:String): Result {
    override fun getMessage(): String {
        return this.message
    }
}