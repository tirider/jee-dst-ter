(function(){
	'use strict';
	var mod = angular.module('global.service');
	mod.factory('myHttpInterceptor', ['$q', '$window', '$rootScope', function ($q, $window, $rootScope) {
		return function (promise) {
			return promise.then(function (response, status) {
				// do something on success
				if(!_(response.config.url).endsWith("html")){
					$rootScope.$broadcast('spinnerOff');
					//console.log('spinnerOff with '+response.config.url);
				}
				return response;
			}, function (response){
				// do something on error
				var alertObj = {type:'error', msg:'Erreurs serveur de STATUS:'+response.status};
				$rootScope.$broadcast('alertOn', alertObj);
				$rootScope.$broadcast('spinnerOff');
				return $q.reject(response);
			});
		};
	}]);

	mod.config(['$httpProvider', function ($httpProvider) {
		$httpProvider.responseInterceptors.push('myHttpInterceptor');
	}]);

	//Navigation
	mod.constant('Navigation',
			[
			 {  when:"/indexation", 
				 templateUrl:"views/indexation.html", 
				 controller:"IndexationCtrl"
			 }, 
			 {  when:"/inscription", 
				 templateUrl:"views/inscription.html", 
				 controller:"InscriptionCtrl"
			 },
			 {  when:"/login", 
				 templateUrl:"views/login.html", 
				 controller:"LoginCtrl"
			 },
			 {  when:"/mydocuments", 
				 templateUrl:"views/mydocuments.html", 
				 controller:"MydocumentsCtrl"
			 },
			 {  when:"/reset", 
				 templateUrl:"views/resetPassword.html", 
				 controller:"ResetPasswordCtrl"
			 }, 
			 {  when:"/search", 
				 templateUrl:"views/search.html", 
				 controller:"SearchCtrl"
			 }, 
			 {  when:"/security", 
				 templateUrl:"views/passwordUpdate.html", 
				 controller:"SecurityCtrl"
			 }, 
			 {  when:"/upload", 
				 templateUrl:"views/upload.html", 
				 controller:"UploadCtrl"
			 }, 
			 {  when:"/welcome", 
				 templateUrl:"views/welcome.html", 
				 controller:"WelcomeCtrl"
			 }
			 ]);

//	Web methodes Facade
	mod.factory("cmWSFacade", 
			['$http', 'serverRESTConfig','$rootScope', function($http, serverRESTConfig, $rootScope){
				var compo_url= serverRESTConfig.protocal+
				serverRESTConfig.domain+':'+
				serverRESTConfig.port+
				serverRESTConfig.context;
				return {
					cmWSGet:function(uri, cache){
						$rootScope.$broadcast('spinnerOn');
						console.log('NOW GET '+compo_url+uri);
						return $http({
							url:compo_url+uri,
							method:"GET", 
							cache:cache, 
							timeout:60000
						}); 
					}, 

					cmWSPost:function(uri, content){
						$rootScope.$broadcast('spinnerOn');
						return $http({
							url:compo_url+uri,
							method:"POST",
							cache:false,
							timeout:60000,
							data:content
						});
					}
				};
			}]);

})();