import { IReservation } from 'app/shared/model/reservation.model';

export interface IHebergement {
    id?: number;
    libele?: string;
    valeur?: number;
    reservations?: IReservation[];
}

export class Hebergement implements IHebergement {
    constructor(public id?: number, public libele?: string, public valeur?: number, public reservations?: IReservation[]) {}
}
