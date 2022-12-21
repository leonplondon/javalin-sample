package pro.darkgod.modules

import io.javalin.core.JavalinConfig
import io.javalin.plugin.metrics.MicrometerPlugin
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmHeapPressureMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.core.instrument.binder.system.UptimeMetrics
import io.micrometer.core.instrument.composite.CompositeMeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry

class MetricsModule private constructor(
    val prometheusMeterRegistry: PrometheusMeterRegistry,
    val simpleMeterRegistry: SimpleMeterRegistry,
) {
    val compositeMeterRegistry: CompositeMeterRegistry by lazy { MetricsModule.compositeMeterRegistry }

    fun register(config: JavalinConfig) {
        compositeMeterRegistry.run {
            plugins.forEach { this.add(it) }
            metrics.forEach { it.bindTo(this) }
        }

        config.registerPlugin(MicrometerPlugin(compositeMeterRegistry))
    }

    companion object {
        private val compositeMeterRegistry = CompositeMeterRegistry()
        private val prometheusMeterRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
        private val simpleMeterRegistry = SimpleMeterRegistry()

        private val plugins: List<MeterRegistry> = listOf(
            prometheusMeterRegistry,
            simpleMeterRegistry,
        )

        private val metrics = listOf(
            JvmGcMetrics(),
            JvmHeapPressureMetrics(),
            ProcessorMetrics(),
            UptimeMetrics(),
        )

        fun create(): MetricsModule {
            return MetricsModule(
                prometheusMeterRegistry,
                simpleMeterRegistry,
            )
        }
    }
}
