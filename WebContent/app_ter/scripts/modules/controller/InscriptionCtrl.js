(function(){
	'use strict';
	var module = angular.module('mod.controller');

	module.controller('InscriptionCtrl', ['$scope', '$location', 'AuthentificationService',
	                                      function($scope, $location, AuthentificationService){
		
		$scope.register = function(){
			console.log('register');
			if(!_.isUndefined($scope.username)&&!_.isUndefined($scope.password)&&!_.isUndefined($scope.email)){
				AuthentificationService.register($scope.username, $scope.email, $scope.password).success(function(data){
					//Analyse result data and redirect to LoginPage
					//...
					
				}).error(function(error, status){
					alert('You have been correctly registered!');
					$location.path("/login");
				});
			}
		};
	}]);
})();