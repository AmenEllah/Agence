import { Moment } from 'moment';
import { IReservation } from 'app/shared/model/reservation.model';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { ILieu } from 'app/shared/model/lieu.model';
import { IEnvironnement } from 'app/shared/model/environnement.model';

export interface IVoyage {
    id?: number;
    dateDepart?: Moment;
    duree?: number;
    description?: any;
    reservations?: IReservation[];
    rubrique?: IRubrique;
    lieuDepart?: ILieu;
    lieuArrive?: ILieu;
    environnement?: IEnvironnement;
}

export class Voyage implements IVoyage {
    constructor(
        public id?: number,
        public dateDepart?: Moment,
        public duree?: number,
        public description?: any,
        public reservations?: IReservation[],
        public rubrique?: IRubrique,
        public lieuDepart?: ILieu,
        public lieuArrive?: ILieu,
        public environnement?: IEnvironnement
    ) {}
}
