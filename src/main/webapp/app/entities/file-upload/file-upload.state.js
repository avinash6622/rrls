(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-upload', {
            parent: 'entity',
            url: '/file-upload',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FileUploads'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-upload/file-uploads.html',
                    controller: 'FileUploadController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('file-upload-detail', {
            parent: 'file-upload',
            url: '/file-upload/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FileUpload'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-upload/file-upload-detail.html',
                    controller: 'FileUploadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FileUpload', function($stateParams, FileUpload) {
                    return FileUpload.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-upload',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('file-upload-detail.edit', {
            parent: 'file-upload-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload/file-upload-dialog.html',
                    controller: 'FileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileUpload', function(FileUpload) {
                            return FileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-upload.new', {
            parent: 'file-upload',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload/file-upload-dialog.html',
                    controller: 'FileUploadDialogController',
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
                                addFileFlag: null,
                                opportunityMasterId:null,
                                createdBy: null,
                                updatedBy: null,
                                createdDate: null,
                                updatedDate: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('file-upload', null, { reload: 'file-upload' });
                }, function() {
                    $state.go('file-upload');
                });
            }]
        })
        .state('file-upload.edit', {
            parent: 'file-upload',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload/file-upload-dialog.html',
                    controller: 'FileUploadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileUpload', function(FileUpload) {
                            return FileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-upload', null, { reload: 'file-upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-upload.delete', {
            parent: 'file-upload',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload/file-upload-delete-dialog.html',
                    controller: 'FileUploadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileUpload', function(FileUpload) {
                            return FileUpload.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-upload', null, { reload: 'file-upload' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
