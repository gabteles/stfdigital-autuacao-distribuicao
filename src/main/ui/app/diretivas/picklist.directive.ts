/*
import IDirective = angular.IDirective;
import IDirectiveFactory = angular.IDirectiveFactory;
import {PicklistController} from "./picklist.controller";

export class PicklistDirective implements IDirective{
    public restrict: string = "E";
    public templateUrl: string;
    public transclude: boolean = true;
    public replace: boolean = true;

    public scope: Object = {
        leftListRowsModel: '=leftListRows',
		rightListRowsModel: '=rightListRows',		
		displayFn: '&',
        toRightCallback: '&',
        toLeftCallback: '&',
        listWidth: '@listWidth',//optional, empty by default
        listHeight: '@listHeight',//optional, empty by default
        showMoveAllButtons : '@' //optional, true by default
    };
    public controller: any = PicklistController;
    public controllerAs: string = 'vm';
    
    constructor(private properties) {
        this.templateUrl = properties.apiUrl + "/distribuicao/diretivas/picklist.tpl.html"; 
        }

    public static factory(): IDirectiveFactory {
        "ngInject";            
        return (properties) => {
            return new PicklistDirective(properties);
        };
    }
}

angular.module("app.novo-processo-distribuicao").directive("picklist"); */