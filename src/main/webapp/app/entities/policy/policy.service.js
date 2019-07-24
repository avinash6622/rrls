(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('PoliciesFileUpload', PoliciesFileUpload);

    PoliciesFileUpload.$inject = ['$resource', 'DateUtils'];

    function PoliciesFileUpload ($resource, DateUtils) {
        var resourceUrl =  'api/policies/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        console.log("data", data)
                        data = angular.fromJson(data);
                        data.dateActive = DateUtils.convertLocalDateFromServer(data.dateActive);
                        data.createdDate = DateUtils.convertLocalDateFromServer(data.createdDate);
                        data.updatedDate = DateUtils.convertDateTimeFromServer(data.updatedDate);
                    }

                    return data;
                }
            },

            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateActive = DateUtils.convertLocalDateToServer(copy.dateActive);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);
                    return angular.toJson(copy);
                }
            },
            'getPolicyFileUploadDetail': {
                url:'api/policies',
                method: 'GET',
                isArray: true},
        });

    }
})();
