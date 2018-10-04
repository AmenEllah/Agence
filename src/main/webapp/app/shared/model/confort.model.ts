import { IReservation } from 'app/shared/model/reservation.model';

export interface IConfort {
    id?: number;
    libele?: string;
    valeur?: number;
    reservations?: IReservation[];
}

export class Confort implements IConfort {
    constructor(public id?: number, public libele?: string, public valeur?: number, public reservations?: IReservation[]) {}
}
