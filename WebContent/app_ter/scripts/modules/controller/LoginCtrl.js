(function(){
	'use strict';
	var module = angular.module('mod.controller');
	module.controller('LoginCtrl', ['$scope', '$rootScope', '$location', 'webStorage', 'AuthentificationService', 
	                                function($scope, $rootScope, $location, webStorage, AuthentificationService){

		$scope.submit = function(){
			console.log($scope.email);
			console.log($scope.password);
			if(!_.isUndefined($scope.email)&&!_.isUndefined($scope.password)){
				AuthentificationService.checkLogin($scope.email, $scope.password).success(function(data){
					//Set WebSession, save user's info into Web Session, use webservice API
					
				}).error(function(error){
					var user_list_docs = [
					                      {
					                    	  "id":"111", 
					                    	  "name":"Doc1", 
					                      },
					                      {
					                    	  "id":"222", 
					                    	  "name":"Doc2"
					                      },
					                      ];					
					var $user_info={
						"name":"gao", 
						"email":"gao@gao.com",
						"listDoc":user_list_docs
					};
					if(webStorage.session.get('$user_info')==null){
						webStorage.session.add('$user_info', $user_info);
					};
					//Redirect to WelcomePage
					$location.path("/welcome");
				});
				
			};
		};
	}]);
})();