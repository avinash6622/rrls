(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('StrategyMaster', StrategyMaster);

    StrategyMaster.$inject = ['$resource', 'DateUtils'];

    function StrategyMaster ($resource, DateUtils) {
        var resourceUrl =  'api/strategy-masters/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dateActive = DateUtils.convertLocalDateFromServer(data.dateActive);
                        data.createdDate = DateUtils.convertLocalDateFromServer(data.createdDate);
                        data.updatedDate = DateUtils.convertDateTimeFromServer(data.updatedDate);
                    }

                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dateActive = DateUtils.convertLocalDateToServer(copy.dateActive);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);
                    return angular.toJson(copy);
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

            'getStrategyDetail': {
                url: 'api/strategy-masters-detail/:id',
                method: 'GET',
                isArray: true
            }
        });

    }
})();
