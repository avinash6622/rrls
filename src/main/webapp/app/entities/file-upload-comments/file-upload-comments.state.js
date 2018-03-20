(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-upload-comments', {
            parent: 'entity',
            url: '/file-upload-comments',
            data: {
                authorities: ['User'],
                pageTitle: 'FileUploadComments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-upload-comments/file-upload-comments.html',
                    controller: 'FileUploadCommentsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('file-upload-comments-detail', {
            parent: 'file-upload-comments',
            url: '/file-upload-comments/{id}',
            data: {
                authorities: ['User'],
                pageTitle: 'FileUploadComments'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-upload-comments/file-upload-comments-detail.html',
                    controller: 'FileUploadCommentsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FileUploadComments', function($stateParams, FileUploadComments) {
                    return FileUploadComments.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'file-upload-comments',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('file-upload-comments-detail.edit', {
            parent: 'file-upload-comments-detail',
            url: '/detail/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload-comments/file-upload-comments-dialog.html',
                    controller: 'FileUploadCommentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileUploadComments', function(FileUploadComments) {
                            return FileUploadComments.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-upload-comments.new', {
            parent: 'file-upload-comments',
            url: '/new',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload-comments/file-upload-comments-dialog.html',
                    controller: 'FileUploadCommentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null,
                                fileComments: null,
                                createdBy: null,
                                updatedBy: null,
                                createdDate: null,
                                updatedDate: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('file-upload-comments', null, { reload: 'file-upload-comments' });
                }, function() {
                    $state.go('file-upload-comments');
                });
            }]
        })
        .state('file-upload-comments.edit', {
            parent: 'file-upload-comments',
            url: '/{id}/edit',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload-comments/file-upload-comments-dialog.html',
                    controller: 'FileUploadCommentsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FileUploadComments', function(FileUploadComments) {
                            return FileUploadComments.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-upload-comments', null, { reload: 'file-upload-comments' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-upload-comments.delete', {
            parent: 'file-upload-comments',
            url: '/{id}/delete',
            data: {
                authorities: ['User']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-upload-comments/file-upload-comments-delete-dialog.html',
                    controller: 'FileUploadCommentsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FileUploadComments', function(FileUploadComments) {
                            return FileUploadComments.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-upload-comments', null, { reload: 'file-upload-comments' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
