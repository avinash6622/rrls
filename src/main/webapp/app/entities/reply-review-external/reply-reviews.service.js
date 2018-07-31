(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('ReplyReview', ReplyReview);

    ReplyReview.$inject = ['$resource', 'DateUtils'];

    function ReplyReview ($resource, DateUtils) {
        var resourceUrl =  'api/reply-review';

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
