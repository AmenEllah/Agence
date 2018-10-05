import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { IVoyage } from 'app/shared/model/voyage.model';
import { VoyageService } from 'app/entities/voyage';
import { IConfort } from 'app/shared/model/confort.model';
import { ConfortService } from 'app/entities/confort';
import { IHebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from 'app/entities/hebergement';

@Component({
    selector: 'jhi-reservation-update',
    templateUrl: './reservation-update.component.html'
})
export class ReservationUpdateComponent implements OnInit {
    private _reservation: IReservation;
    isSaving: boolean;
    idVoyage: number;
    etat: string;
    voyages: IVoyage[];

    conforts: IConfort[];

    hebergements: IHebergement[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private reservationService: ReservationService,
        private voyageService: VoyageService,
        private confortService: ConfortService,
        private hebergementService: HebergementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.idVoyage = this.voyageService.idVoayage;
        this.etat="En attente de payement"

        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reservation }) => {
            this.reservation = reservation;
        });
        this.voyageService.query().subscribe(
            (res: HttpResponse<IVoyage[]>) => {
                this.voyages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.confortService.query().subscribe(
            (res: HttpResponse<IConfort[]>) => {
                this.conforts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.hebergementService.query().subscribe(
            (res: HttpResponse<IHebergement[]>) => {
                this.hebergements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reservation.id !== undefined) {
            this.subscribeToSaveResponse(this.reservationService.update(this.reservation));
        } else {
            this.subscribeToSaveResponse(this.reservationService.create(this.reservation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>) {
        result.subscribe((res: HttpResponse<IReservation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackVoyageById(index: number, item: IVoyage) {
        return item.id;
    }

    trackConfortById(index: number, item: IConfort) {
        return item.id;
    }

    trackHebergementById(index: number, item: IHebergement) {
        return item.id;
    }
    get reservation() {
        return this._reservation;
    }

    set reservation(reservation: IReservation) {
        this._reservation = reservation;
    }
}
