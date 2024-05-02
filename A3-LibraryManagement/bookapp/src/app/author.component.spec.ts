import { TestBed } from '@angular/core/testing';
import { AuthorComponent } from './author.component'; 

describe('AuthorComponent', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AuthorComponent],
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AuthorComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have the 'bookapp' title`, () => {
    const fixture = TestBed.createComponent(AuthorComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('bookapp');
  });

  it('should render title', () => {
    const fixture = TestBed.createComponent(AuthorComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Hello, bookapp');
  });
});
