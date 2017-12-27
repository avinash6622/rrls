(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FileUploadDetailController', FileUploadDetailController);

    FileUploadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'FileUpload', 'OpportunityMaster'];

    function FileUploadDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, FileUpload, OpportunityMaster) {
        var vm = this;

        vm.fileUpload = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:fileUploadUpdate', function(event, result) {
            vm.fileUpload = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
