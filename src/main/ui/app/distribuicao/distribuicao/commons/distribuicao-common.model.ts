import Command = app.support.command.Command;


export const API_DISTRIBUICAO = '/distribuicao/api/distribuicao';
export const API_MINISTROS = '/distribuicao/api/ministros';



export class TipoDistribuicao {
    constructor(public id: string, public exigeJustificativa : boolean) {}
}

export class Parte {
    constructor(public apresentacao: string, 
                public pessoa?: number,
                public polo?: string) {}
}

export class Preferencia {
    constructor(public id: number, public nome: string) {}
}

export class Classe {
    constructor(public id: string, public nome: string,
                public preferencias: Array<Preferencia>) {}
}

export interface Processo {
    
    processoId: number;
    status: string;
    classe: Classe;
    numero: number;
    identificacao: string;
    autuador: string;
    meioTramitacao: string;
    sigilo: string;
    partes: Array<Parte>;
    preferencias: Array<Preferencia>;
}

export interface Distribuicao {
    
    processoId: number;
    status: string;
    classe: Classe;
    numero: number;
    identificacao: string;
    autuador: string;
    meioTramitacao: string;
    sigilo: string;
    partes: Array<Parte>;
    preferencias: Array<Preferencia>;
}

export class DistribuirCommand implements Command {
    public distribuicaoId: number;
    public justificativa: string;
    public tipoDistribuicao: string;

    constructor() {}
}

export class DistribuirProcessoComumCommand extends DistribuirCommand {
    public ministrosCandidatos: Array<number> = [];
    public ministrosImpedidos: Array<number> = [];
    public processosPreventos: Array<number> = [];
    
    constructor() {
        super();
    }
}

export class DistribuirProcessoPrevencaoCommand extends DistribuirCommand {
    public processosPreventos: Array<number> = [];
    
    constructor() {
        super();
    }
}

export class Ministro {
	public id : number;
    public nome : string;
    constructor(id: number, nome: string) {}
}