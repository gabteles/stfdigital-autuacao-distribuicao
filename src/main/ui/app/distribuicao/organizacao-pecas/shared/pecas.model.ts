import Command = app.support.command.Command;

export const API_PECAS = '/distribuicao/api/organizacao-pecas';

export class TipoPeca {
    constructor(public id: number, public nome: string) {}
}

export class PecaSelecionavel {
    constructor(public processoId: number, public peca: Peca) {}
}


export class OrganizaPecasCommand  implements Command {
    public distribuicaoId: number;
    public pecas: Array<Number>;
    public finalizarTarefa: boolean;

    constructor() {}
}

export class Peca {
    public pecaId: number;
    public documentoId: number;
    public tipoPeca: string;
    public descricao: string;
    public numeroOrdem: number;
    public visibilidade: string;
    public situacao: string;

    constructor() {}
}

export class PecasCommand implements Command{
    public processoId : number;
    public pecas : Array<number> = [];
    
    constructor(){}
}

export class ExcluirPecasCommand extends PecasCommand{
    constructor(){
        super();
    }
}

export class JuntarPecaCommand extends PecasCommand{
    constructor(){
        super();
    }
}

export interface Processo {
    
    processoId: number;
    relator: number;
    pecas: Array<Peca>;
}