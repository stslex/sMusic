package com.stslex.core.ui.components.shader

import org.intellij.lang.annotations.Language

@Language("AGSL")
const val ShaderSource = """
    uniform shader composable;
    
    uniform float cutoff;
    uniform float3 rgb;
    
    half4 main(float2 fragCoord) {
        half4 color = composable.eval(fragCoord);
        color.a = step(cutoff, color.a);
        if (color == half4(0.0, 0.0, 0.0, 1.0)) {
            color.rgb = half3(rgb[0], rgb[1], rgb[2]);
        }
        return color;
    }
"""