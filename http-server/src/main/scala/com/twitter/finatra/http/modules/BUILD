scala_library(
    sources = ["*.scala"],
    compiler_option_sets = ["fatal_warnings"],
    provides = scala_artifact(
        org = "com.twitter",
        name = "finatra-http-modules",
        repo = artifactory,
    ),
    strict_deps = "True",
    dependencies = [
        "3rdparty/jvm/com/google/inject:guice",
        "3rdparty/jvm/javax/inject:javax.inject",
        "3rdparty/jvm/net/codingwell:scala-guice",
        "finagle/finagle-base-http/src/main",
        "finagle/finagle-core/src/main",
        "finagle/finagle-http/src/main/scala",
        "finatra/http-server/src/main/scala/com/twitter/finatra/http/contexts",
        "finatra/http-server/src/main/scala/com/twitter/finatra/http/exceptions",
        "finatra/http-server/src/main/scala/com/twitter/finatra/http/internal/exceptions",
        "finatra/http-server/src/main/scala/com/twitter/finatra/http/internal/exceptions/json",
        "finatra/http-server/src/main/scala/com/twitter/finatra/http/internal/marshalling",
        "finatra/http-server/src/main/scala/com/twitter/finatra/http/response",
        "finatra/inject/inject-app/src/main/java/com/twitter/inject/annotations",
        "finatra/inject/inject-core/src/main/scala",
        "finatra/inject/inject-modules/src/main/scala/com/twitter/inject/modules",
        "finatra/jackson/src/main/scala/com/twitter/finatra/jackson/caseclass",
        "finatra/utils/src/main/java/com/twitter/finatra/annotations",
        "finatra/utils/src/main/scala/com/twitter/finatra/modules",
        "util/util-app/src/main/scala",
        "util/util-stats/src/main/scala",
    ],
    exports = [
        "finatra/inject/inject-core/src/main/scala",
    ],
)
