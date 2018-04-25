(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('AnswerComment', AnswerComment);

    AnswerComment.$inject = ['$resource', 'DateUtils'];

    function AnswerComment ($resource, DateUtils) {
        var resourceUrl =  'api/answer-comment';

        return $resource(resourceUrl, {}, {
            'save':{
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
