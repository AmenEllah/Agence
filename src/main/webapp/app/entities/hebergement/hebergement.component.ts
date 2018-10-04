import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHebergement } from 'app/shared/model/hebergement.model';
import { Principal } from 'app/core';
import { HebergementService } from './hebergement.service';

@Component({
    selector: 'jhi-hebergement',
    templateUrl: './hebergement.component.html'
})
export class HebergementComponent implements OnInit, OnDestroy {
    hebergements: IHebergement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hebergementService: HebergementService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.hebergementService.query().subscribe(
            (res: HttpResponse<IHebergement[]>) => {
                this.hebergements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHebergements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHebergement) {
        return item.id;
    }

    registerChangeInHebergements() {
        this.eventSubscriber = this.eventManager.subscribe('hebergementListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
