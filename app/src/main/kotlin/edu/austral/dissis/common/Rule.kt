package edu.austral.dissis.common

import edu.austral.dissis.common.interfaces.Validator


class Rule(private val andValidators: List<Validator>, private val orValidators: List<Validator>) {

    fun getAndValidators(): List<Validator> {
        return andValidators
    }

    fun getOrValidators(): List<Validator> {
        return orValidators
    }

}