(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityMaster', OpportunityMaster);

    OpportunityMaster.$inject = ['$resource', 'DateUtils'];

    function OpportunityMaster ($resource, DateUtils) {
        var resourceUrl =  'api/opportunity-masters/:id/:inputData';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
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
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);
                    return angular.toJson(copy);
                }
            },
            'saveDoc': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.createdDate = DateUtils.convertLocalDateToServer(copy.createdDate);
                    return angular.toJson(copy);
                }
            },
            'saveWithUpload': {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                transformRequest: function (data) {
                    var formdata = new FormData();
                    formdata.append('fileUpload', data.fileUpload);
                    return formdata;
                },
                params: {strategyId: '@inputData.strategyId', oppDescription: '@inputData.oppDescription'}
            },
            'getFileContent': {
            	url: 'api/opportunity-masters/get-file/:id',
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertLocalDateFromServer(data.createdDate);
                        data.updatedDate = DateUtils.convertDateTimeFromServer(data.updatedDate);
                    }
                    return data;
                }
            },
            'wordCreation': {
            	url: 'api/opportunity-masters/create-file/',
                method: 'POST',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertLocalDateFromServer(data.createdDate);
                        data.updatedDate = DateUtils.convertDateTimeFromServer(data.updatedDate);
                    }
                    return data;
                }
            },

            'description':{

                url:'api/opportunity-masters/description',
                method:'PUT',

                transformResponse: function(data){

                     console.log(data);


               }


            }








            /*,
            'addWordCreation':{
            	url: 'api/opportunity-masters/additional-word-file/',
                method: 'POST',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertLocalDateFromServer(data.createdDate);
                        data.updatedDate = DateUtils.convertDateTimeFromServer(data.updatedDate);
                    }
                    return data;
                }

            }*/
        });



    }
})();
