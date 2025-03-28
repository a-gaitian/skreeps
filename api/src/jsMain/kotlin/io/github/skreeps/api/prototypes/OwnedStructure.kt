package io.github.skreeps.api.prototypes

import io.github.skreeps.api.constants.Find.*

/**
 * The base prototype for a structure that has an owner. Such structures can be found
 * using [FIND_MY_STRUCTURES] and [FIND_HOSTILE_STRUCTURES] constants
 */
abstract external class OwnedStructure: Structure {

    /**
     * Whether this is your own structure
     */
    val my: Boolean

    /**
     * An object with the structure’s owner info
     */
    val owner: Owner
}

external class Owner {

    /**
     * The name of the owner user
     */
    val username: String
}
