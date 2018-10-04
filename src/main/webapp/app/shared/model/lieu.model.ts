import { IVoyage } from 'app/shared/model/voyage.model';
import { IImage } from 'app/shared/model/image.model';
import { IRubrique } from 'app/shared/model/rubrique.model';

export interface ILieu {
    id?: number;
    libele?: string;
    voyage1S?: IVoyage[];
    voyage2S?: IVoyage[];
    images?: IImage[];
    rubriques?: IRubrique[];
}

export class Lieu implements ILieu {
    constructor(
        public id?: number,
        public libele?: string,
        public voyage1S?: IVoyage[],
        public voyage2S?: IVoyage[],
        public images?: IImage[],
        public rubriques?: IRubrique[]
    ) {}
}
