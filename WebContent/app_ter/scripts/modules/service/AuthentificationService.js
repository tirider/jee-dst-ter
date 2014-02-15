(function(){
	'use strict';
	var mod = angular.module('mod.service');
	
	mod.factory('AuthentificationService', ['$rootScope', 'cmWSFacade', 'webStorage',
	                               function($rootScope, cmWSFacade,webStorage){
		return{
			//loginCtrl
			checkLogin:function(email, password){
				return cmWSFacade.cmWSGet('AuthentificationService/loginAction?email='+email+'&?password='+password, false);
			}, 
			
			//InscriptionCtrl
			register:function(username, email, password){
				return cmWSFacade.cmWSGet('AuthentificationService/registerAction?username=='+username+'+&?email='+email+'&?password='+password, false);
			}
		};
	}]);
	                            
})();