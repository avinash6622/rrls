(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp', [
            'ngStorage',
            'ngResource',
            'ngCookies',
            'ngAria',
            'ngCacheBuster',
            'ngFileUpload',
            'ui.bootstrap',
            'ui.bootstrap.datetimepicker',
            'ui.router',
            'infinite-scroll',
            // jhipster-needle-angularjs-add-module JHipster will add new module here
            'angular-loading-bar',
            'ckeditor',
            'autoCompleteModule',
           // 'xyScroll',
            'angular-notification-icons',
           // 'angucomplete-alt'
            'rzModule',
            'ngSanitize',
            'angular-decimals'
        ])
        .run(run);

    run.$inject = ['stateHandler'];

    function run(stateHandler) {
        stateHandler.initialize();
    }
})();
