(function() {
    'use strict';
    angular
        .module('researchRepositoryLearningSystemApp')
        .factory('OpportunityMaster', OpportunityMaster);

    OpportunityMaster.$inject = ['$resource', 'DateUtils','$window'];

    function OpportunityMaster ($resource, DateUtils,$window) {

        // alert("hii");
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




               }


            },
         /*  'questionAnswer':{
                url:'api/opportunity-masters/get-Question/:id',
                method:'GET',

                transformResponse: function(data){
                	console.log('questiondata',data);
                	var data1=data
                	return data1;
               }


            },

*/
            'downloadfilecontent':{
                url:'api/opportunity-masters/download-file/',
                method:'GET',
                transformResponse: function(data){



                }


            },
            'summarydatavalues':{
                url:'api/opportunity-summary',
                method:'POST',
                transformResponse:function(data){

                }
            },

            'getsummarydata':{
                url:'api/opportunity-summary/getdata/',
                method:'GET',
                transformResponse:function(data){



                    var copy = angular.copy(data);

                    return data;



                }
            },

            'upload':{

                url:'api/file-upload-data',
                method:'POST',
                transformResponse: function(data){
                }

            },
            'notification':{

                url:'api/store_noti_user_id',
                method:'POST'
            },
         
           'opportunitysummaryData':{
                url:'api/opportunity-summary/getdata',
                method:'GET',
               isArray: true
           },

            'opportunitysummaryDataCreatedBy':{
                url:'api/opportunity-summary/getdata/:createdBy',
                method:'GET',
                isArray: true

            },

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
           'getSearchOpportunity': {
           	url: 'api/opportunity-masters/search',
               method: 'POST',
               isArray: true
           },
           'queryOpportunity':{
               url:'api/opportunity-master-all',
               method:'GET',
              isArray: true
             
          },
          'tagOpportunity':{
              url:'api/opportunity-master-learn',
              method:'GET',
             isArray: true
            
         },
        });



    }
})();
