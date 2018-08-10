(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('UserDelegationAudit', UserDelegationAudit);

    UserDelegationAudit.$inject = ['$resource', 'DateUtils'];

    function UserDelegationAudit ($resource, DateUtils) {
        var resourceUrl =  'api/oppName_userName';

        return $resource(resourceUrl, {}, {

            'save':{
                method:'POST',
                transformRequest: function (data) {

                    var copy = angular.copy(data);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

                    return angular.toJson(copy);
                }
            },
            'admin':{
                method:'GET',
                url: 'api/user-delegation/:fromName',
                isArray: true
            },
            'delegateAdmin':{
                method:'POST',
                url: 'api/delegate-admin',
                transformRequest: function (data) {

                    var copy = angular.copy(data);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);

                    return angular.toJson(copy);
                }
            }
        });
    }
})();
