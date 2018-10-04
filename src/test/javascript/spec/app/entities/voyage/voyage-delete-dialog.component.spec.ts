/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AgenceTestModule } from '../../../test.module';
import { VoyageDeleteDialogComponent } from 'app/entities/voyage/voyage-delete-dialog.component';
import { VoyageService } from 'app/entities/voyage/voyage.service';

describe('Component Tests', () => {
    describe('Voyage Management Delete Component', () => {
        let comp: VoyageDeleteDialogComponent;
        let fixture: ComponentFixture<VoyageDeleteDialogComponent>;
        let service: VoyageService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AgenceTestModule],
                declarations: [VoyageDeleteDialogComponent]
            })
                .overrideTemplate(VoyageDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(VoyageDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VoyageService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
