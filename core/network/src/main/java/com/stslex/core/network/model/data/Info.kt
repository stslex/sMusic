package com.stslex.core.network.model.data

import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Runs

internal data class Info<T : NavigationEndpoint.Endpoint>(
    val name: String?,
    val endpoint: T?
) {
    @Suppress("UNCHECKED_CAST")
    constructor(run: Runs.Run) : this(
        name = run.text,
        endpoint = run.navigationEndpoint?.endpoint as T?
    )
}