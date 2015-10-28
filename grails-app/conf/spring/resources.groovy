// Place your Spring DSL code here
beans = {

    lolHttpClient(ConnectionPoolingHttpBuilder) { bean ->
        bean.constructorArgs = [[defaultURI : 'https://na.api.pvp.net/',
                                 authUser : 'mcsirius',
                                 authPassword : 'cathy910209']]
    }


}
