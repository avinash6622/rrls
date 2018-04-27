(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('ReplyComment', ReplyComment);

    ReplyComment.$inject = ['$resource', 'DateUtils'];

    function ReplyComment ($resource, DateUtils) {
        var resourceUrl =  'api/reply-comment';

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
