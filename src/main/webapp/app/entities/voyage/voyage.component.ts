import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IVoyage } from 'app/shared/model/voyage.model';
import { Principal } from 'app/core';
import { VoyageService } from './voyage.service';

@Component({
    selector: 'jhi-voyage',
    templateUrl: './voyage.component.html'
})
export class VoyageComponent implements OnInit, OnDestroy {
    voyages: IVoyage[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private voyageService: VoyageService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.voyageService.query().subscribe(
            (res: HttpResponse<IVoyage[]>) => {
                this.voyages = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInVoyages();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IVoyage) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInVoyages() {
        this.eventSubscriber = this.eventManager.subscribe('voyageListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
