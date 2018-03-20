(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('additional-file-upload', {
            parent: 'entity',
            url: '/additional-file-upload',
            data: {
                authorities: ['User'],
                pageTitle: 'AdditionalFileUploads'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/additional-file-upload/additional-file-uploads.html',
                    controller: 'AdditionalFileUploadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('additional-file-upload-detail', {
            parent: 'additional-file-upload',
            url: '/additional-file-upload/{id}',
            data: {
                authorities: ['User'],
                pageTitle: 'AdditionalFileUpload'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/additional-file-upload/additional-file-upload-detail.html',
                    controller: 'AdditionalFileUploadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AdditionalFileUpload', function($stateParams, AdditionalFileUpload) {
                    return AdditionalFileUpload.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'additional-file-upload',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('additional-file-upload-detail.edit', {
            parent: 'additional-file-upload-detail',
            url: '/detail/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-file-upload/additional-file-upload-dialog.html',
                    controller: 'AdditionalFileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdditionalFileUpload', function(AdditionalFileUpload) {
                            return AdditionalFileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('additional-file-upload.new', {
            parent: 'additional-file-upload',
            url: '/new',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-file-upload/additional-file-upload-dialog.html',
                    controller: 'AdditionalFileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null,
                                fileName: null,
                                fileData: null,
                                fileDataContentType: null,
                                createdBy: null,
                                updatedBy: null,
                                createdDate: null,
                                updatedDate: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('additional-file-upload', null, { reload: 'additional-file-upload' });
                }, function() {
                    $state.go('additional-file-upload');
                });
            }]
        })
        .state('additional-file-upload.edit', {
            parent: 'additional-file-upload',
            url: '/{id}/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-file-upload/additional-file-upload-dialog.html',
                    controller: 'AdditionalFileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AdditionalFileUpload', function(AdditionalFileUpload) {
                            return AdditionalFileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('additional-file-upload', null, { reload: 'additional-file-upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('additional-file-upload.delete', {
            parent: 'additional-file-upload',
            url: '/{id}/delete',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/additional-file-upload/additional-file-upload-delete-dialog.html',
                    controller: 'AdditionalFileUploadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AdditionalFileUpload', function(AdditionalFileUpload) {
                            return AdditionalFileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('additional-file-upload', null, { reload: 'additional-file-upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
