import { ILieu } from 'app/shared/model/lieu.model';

export interface IImage {
    id?: number;
    nameContentType?: string;
    name?: any;
    lieu?: ILieu;
}

export class Image implements IImage {
    constructor(public id?: number, public nameContentType?: string, public name?: any, public lieu?: ILieu) {}
}
