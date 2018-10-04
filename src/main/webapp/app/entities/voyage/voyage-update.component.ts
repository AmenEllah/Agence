import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IVoyage } from 'app/shared/model/voyage.model';
import { VoyageService } from './voyage.service';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from 'app/entities/rubrique';
import { ILieu } from 'app/shared/model/lieu.model';
import { LieuService } from 'app/entities/lieu';
import { IEnvironnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from 'app/entities/environnement';

@Component({
    selector: 'jhi-voyage-update',
    templateUrl: './voyage-update.component.html'
})
export class VoyageUpdateComponent implements OnInit {
    private _voyage: IVoyage;
    isSaving: boolean;

    rubriques: IRubrique[];

    lieus: ILieu[];

    environnements: IEnvironnement[];
    dateDepartDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private voyageService: VoyageService,
        private rubriqueService: RubriqueService,
        private lieuService: LieuService,
        private environnementService: EnvironnementService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ voyage }) => {
            this.voyage = voyage;
        });
        this.rubriqueService.query().subscribe(
            (res: HttpResponse<IRubrique[]>) => {
                this.rubriques = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.lieuService.query().subscribe(
            (res: HttpResponse<ILieu[]>) => {
                this.lieus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.environnementService.query().subscribe(
            (res: HttpResponse<IEnvironnement[]>) => {
                this.environnements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.voyage.id !== undefined) {
            this.subscribeToSaveResponse(this.voyageService.update(this.voyage));
        } else {
            this.subscribeToSaveResponse(this.voyageService.create(this.voyage));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVoyage>>) {
        result.subscribe((res: HttpResponse<IVoyage>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRubriqueById(index: number, item: IRubrique) {
        return item.id;
    }

    trackLieuById(index: number, item: ILieu) {
        return item.id;
    }

    trackEnvironnementById(index: number, item: IEnvironnement) {
        return item.id;
    }
    get voyage() {
        return this._voyage;
    }

    set voyage(voyage: IVoyage) {
        this._voyage = voyage;
    }
}
