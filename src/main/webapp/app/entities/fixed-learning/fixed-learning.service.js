(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('FixedLearning', FixedLearning);

    FixedLearning.$inject = ['$resource', 'DateUtils'];

    function FixedLearning ($resource, DateUtils) {
        var resourceUrl =  'api/fixed-learning/:inputData';

        return $resource(resourceUrl, {}, {
        	
        'update':{
        	
            method:'POST',
            transformRequest: function (data) {
                var copy = angular.copy(data);
               
                return angular.toJson(copy);
            	
            }
        },
        'fixedLearning':{
        	
      	  url:'api/fixed-learning',
            method:'GET',
            isArray: true
      }
        });
    }
})();
