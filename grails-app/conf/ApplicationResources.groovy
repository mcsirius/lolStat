modules = {
    core {
        dependsOn 'jquery'
        dependsOn 'bootstrap'
    }

    application {
        resource url:'js/application.js'
    }
}