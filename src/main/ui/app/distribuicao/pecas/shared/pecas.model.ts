import Command = app.support.command.Command;

export const API_PECAS = '/distribuicao/api/organizacao-pecas';

export class TipoPeca {
    constructor(public id: number, public nome: string) {}
}


export class PecasCommand implements Command {
    public distribuicaoId: number;
    public justificativa: string;
    public tipoDistribuicao: string;

    constructor() {}
}

export interface Peca {
    pecaId: number;
    documentoId: number;
    tipo: string;
    descricao: string;
    numeroOrdem: number;
    visibilidade: string;
    situacao: string;
}

export interface Processo {
    
    processoId: number;
    relator: number;
    pecas: Array<Peca>;
}