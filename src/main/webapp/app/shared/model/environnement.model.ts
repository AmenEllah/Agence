import { IVoyage } from 'app/shared/model/voyage.model';

export interface IEnvironnement {
    id?: number;
    libele?: string;
    voyages?: IVoyage[];
}

export class Environnement implements IEnvironnement {
    constructor(public id?: number, public libele?: string, public voyages?: IVoyage[]) {}
}
