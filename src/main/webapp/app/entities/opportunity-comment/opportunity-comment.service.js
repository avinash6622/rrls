(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('CommentOpportunity', CommentOpportunity);

    CommentOpportunity.$inject = ['$resource', 'DateUtils'];

    function CommentOpportunity ($resource, DateUtils) {
        var resourceUrl =  'api/opportunity-masters/get-comment/:id';

        return $resource(resourceUrl, {}, {
            'comment':{
                method:'GET',
                isArray: true
            },
            'save':{
                url:'api/opportunity-comment',
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
