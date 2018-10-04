import { IVoyage } from 'app/shared/model/voyage.model';
import { IConfort } from 'app/shared/model/confort.model';
import { IHebergement } from 'app/shared/model/hebergement.model';

export interface IReservation {
    id?: number;
    nbrAdultes?: number;
    nbrEnfants?: number;
    etat?: string;
    voyage?: IVoyage;
    confort?: IConfort;
    hebergement?: IHebergement;
}

export class Reservation implements IReservation {
    constructor(
        public id?: number,
        public nbrAdultes?: number,
        public nbrEnfants?: number,
        public etat?: string,
        public voyage?: IVoyage,
        public confort?: IConfort,
        public hebergement?: IHebergement
    ) {}
}
