import { IVoyage } from 'app/shared/model/voyage.model';
import { ILieu } from 'app/shared/model/lieu.model';

export interface IRubrique {
    id?: number;
    libele?: string;
    voyages?: IVoyage[];
    lieus?: ILieu[];
}

export class Rubrique implements IRubrique {
    constructor(public id?: number, public libele?: string, public voyages?: IVoyage[], public lieus?: ILieu[]) {}
}
