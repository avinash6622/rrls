(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('ReviewExternal', ReviewExternal);

    ReviewExternal.$inject = ['$resource', 'DateUtils'];

    function ReviewExternal ($resource, DateUtils) {
        var resourceUrl =  'api/external-research/get-reviews/:id';

        return $resource(resourceUrl, {}, {
            'reviews':{
                method:'GET',
                isArray: true
            },
            'save':{
                url:'api/external-review',
                method:'POST',
                transformRequest: function (data) {

                    var copy = angular.copy(data);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
