package com.stslex.core.network.data.model

import com.stslex.core.network.model.response.NavigationEndpoint
import com.stslex.core.network.model.response.Runs

data class Info<T : NavigationEndpoint.Endpoint>(
    val name: String?,
    val endpoint: T?
) {
    @Suppress("UNCHECKED_CAST")
    constructor(run: Runs.Run) : this(
        name = run.text,
        endpoint = run.navigationEndpoint?.endpoint as T?
    )
}